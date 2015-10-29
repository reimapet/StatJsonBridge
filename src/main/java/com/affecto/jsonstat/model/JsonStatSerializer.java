package com.affecto.jsonstat.model;

import java.util.Iterator;

public class JsonStatSerializer {

    public String serialize(Dataset dataset) {
        final StringBuffer sb = new StringBuffer();
        sb.append("{");
        appendBasicData(sb, dataset);
        appendDimensions(sb, dataset);
        appendValues(sb, dataset);
        sb.append("}");
        return sb.toString();
    }

    private void appendValues(StringBuffer sb, Dataset dataset) {
    }

    private void appendDimensions(StringBuffer sb, Dataset dataset) {
        sb.append("dimension: {");
        Iterator<Dimension> it = dataset.getDimensions().iterator();
        sb.append("id : [");
        while (it.hasNext()) {
            final Dimension d = it.next();
            sb.append('"').append(d.getId()).append('"');
            if (it.hasNext())
                sb.append(",");

        }
        sb.append("], ");

        it = dataset.getDimensions().iterator();
        sb.append("size : [");
        while (it.hasNext()) {
            final Dimension d = it.next();
            sb.append('"').append(d.getSize()).append('"');
            if (it.hasNext())
                sb.append(",");

        }
        sb.append("],");

        it = dataset.getDimensions().iterator();
        while (it.hasNext()) {
            final Dimension d = it.next();
            sb.append(d.getId()).append(": {");
            sb.append("label: \"").append(d.getLabel()).append("\", ");

            sb.append("category : {");
            sb.append("index: [");
            for (int i = 0; i < d.getSize(); i++) {
                if (i > 0)
                    sb.append(",");
                sb.append('"').append(d.getIndexValue(i)).append('"');
            }

            sb.append("]");
            sb.append("}");

            sb.append("}");
            if (it.hasNext())
                sb.append(",");
        }

        sb.append(", value: [");
        final Iterator<String> dit = dataset.getValues().iterator();
        while (dit.hasNext()) {
            sb.append('"').append(dit.next()).append('"');
            if (dit.hasNext())
                sb.append(",");
        }

        sb.append("]");

    }

    private void appendBasicData(StringBuffer sb, Dataset dataset) {
        sb.append("class: \"dataset:\",");
        sb.append("href: ");
        sb.append('"').append(dataset.getHref()).append("\",");
        sb.append("label: ");
        sb.append('"').append(dataset.getLabel()).append("\", ");
    }
}
