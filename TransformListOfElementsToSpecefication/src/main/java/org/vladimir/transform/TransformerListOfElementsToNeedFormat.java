package org.vladimir.transform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Sentsov on 12.05.2016.
 */
public class TransformerListOfElementsToNeedFormat {

    static String HEADER = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" +
            "        \"http://www.w3.org/TR/html4/loose.dtd\">";

    Collection<Element> getElementsFromFile(String fileName) throws IOException {
        final Map<String, Element> elementMap = new HashMap<>(500);
        Files.lines(Paths.get(fileName))
                .filter(s1 -> !s1.isEmpty())
                .map(s -> s.split("\\t"))
                .forEach(st -> elementMap.merge(st[1], new Element(st), (elem, u) -> elem.addElement(st)));
        return elementMap.values().stream()
                .sorted((e, e1) -> e.elementsClass.get(0).compareTo(e1.elementsClass.get(0))).collect(Collectors.toList());
    }

    String convertElementsToHTML(String fileName) {
        final int[] countRow = {1};
        Collection<Element> elements = null;
        try {
            elements = getElementsFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace(System.out);
            throw new NullPointerException("Не удалось создать коллекцию");
        }

        final StringBuilder builder = new StringBuilder(HEADER);
        builder.append("<html>").append("\n")
                .append("<meta charset=\"UTF-8\"> ")
                .append("<head>").append("\n")
                .append("<title>").append("</title>").append("\n")
                .append("</head>").append("\n");
        builder.append("<body>").append("\n");
        builder.append("<table border=\"1\"> ").append("\n");

        builder.append("<tr>")
                .append("<th>").append("№").append("</th>")
                .append("<th>").append("Название").append("</th>")
                .append("<th>").append("Количесво").append("</th>")
                .append("<th>").append("Обозначения").append("</th>")
                .append("</tr>");

        elements.forEach(element ->

                builder.append("<tr>").append("\n")
                        .append("<td>").append(countRow[0]++).append("</td>").append("\n")
                        .append("<td>").append(element.name).append("</td>").append("\n")
                        .append("<td>").append(element.count).append("</td>").append("\n")
                        .append("<td>").append(elementClassToString(element)).append("</td>").append("\n")
                        .append("</tr>")
        );

        builder.append("</table>").append("\n");
        builder.append("</body>").append("\n");
        builder.append("</html");
        return builder.toString();
    }


    static class Element {
        List<String> elementsClass;
        int count;
        String name;

        Element(String... strings) {
            elementsClass = new ArrayList<>();
            elementsClass.add(strings[0]);
            count = Integer.valueOf(strings[2]);
            name = strings[1];
        }

        Element addElement(String[] strings) {
            elementsClass.add(strings[0]);
            count += Integer.valueOf(strings[2]);
            name = strings[1];
            return this;
        }
    }


    String elementClassToString(Element element) {
        return element.elementsClass.stream().collect(Collectors.joining(","));

    }
}
