package com.affecto.jsonstat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class FormatTest {

    private static InputStream fromTestClassPath(final String path) {
        return SerializationRoundtripTest.class.getClassLoader().getResourceAsStream(path);
    }

    @Test
    public void checkFormat() throws Exception{
        try (final InputStream is = fromTestClassPath("format.html")) {
            final Document doc = Jsoup.parse(is, "UTF-8", "http://json-stat.org/format/");
            doc.select("section[id]").stream()
                    .forEach(e -> {
                        final String id = e.attr("id");
                        System.err.println("id: " + id);

                        final String name = e.select("h3").first().childNode(0).toString();
                        System.err.println("name: " + name);

                        final Elements badges = e.select("span.badge");

                        final List<String> dataTypes = badges.stream()
                                .filter(be -> be.classNames().size() == 1)
                                .map(be -> be.childNode(0).toString())
                                .collect(Collectors.toList());

                        final Boolean isOptional = badges.stream()
                                .anyMatch(be -> be.classNames().contains("label-info")
                                        && !be.getElementsContainingText("optional").isEmpty());

                        System.err.println("types: " + dataTypes);

                        System.err.println("optional: " + isOptional);

                        System.err.println("badges: " + badges);

                        final List<Node> parents = e.select("table.table-doc th:contains(Parents) ~ td")
                                .first().childNodesCopy().stream()
                                .filter(n -> n.toString().startsWith("None") || n.nodeName().equals("a"))
                                .collect(Collectors.toList());
                        System.err.println("parents: " + parents);

                        final List<Node> children = e.select("table.table-doc th:contains(Children) ~ td")
                                .first().childNodesCopy().stream()
                                .filter(n -> n.toString().startsWith("None") || n.nodeName().equals("a"))
                                .collect(Collectors.toList());
                        System.err.println("children: " + children);

                        System.err.println();
                    });
        }
    }



}
