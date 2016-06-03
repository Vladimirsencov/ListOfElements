package org.vladimir.transform;

import gnu.trove.decorator.TCharListDecorator;
import gnu.trove.list.array.TCharArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Created by Sentsov on 12.05.2016.
 */
public class TransformerListOfElementsToNeedFormat {

    static String HEADER = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" +
            "        \"http://www.w3.org/TR/html4/loose.dtd\">\n";

    Collection<Element> getElementsFromFile(String fileName) throws IOException {
        final Map<String, Element> elementMap = new HashMap<>(500);
        Files.lines(Paths.get(fileName))
                .filter(s1 -> !s1.isEmpty())
                .map(s2 -> s2.replaceAll(Character.toString((char) 8211), Character.toString((char) 45)))
                .filter(s -> s.contains("Резистор" ))
//                .filter(s -> s.contains("Panasonic"))
                .map(s -> s.split("\\t"))
                .filter(strings -> {
                if(strings.length<3)
                System.out.println(Arrays.toString(strings)); return strings.length==3;})
               .forEach(st -> elementMap.merge(st[1], new Element(st), (elem, u) -> elem.addElement(st)));
        return elementMap.values().stream()
                .sorted((e, e1) -> e.elementsClass.get(0).compareTo(e1.elementsClass.get(0))).collect(toList());
    }

    String convertElementsToHTML(String fileName) {
        final int[] countRow = {1};
        Collection<Element> elements;
        try {
            elements = getElementsFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace(System.out);
            throw new NullPointerException("Не удалось создать коллекцию");
        }

        final StringBuilder builder = getStringBuilderForHTML(countRow, elements);

        return builder.toString();
    }

    String convertElementsToHTML(String fileName, String... filterParameter) {
        final int[] countRow = {1};
        Collection<Element> elements = null;
        try {
            elements = getElementsFromFile(fileName, filterParameter);
        } catch (IOException e) {
            e.printStackTrace(System.out);
            throw new NullPointerException("Не удалось создать коллекцию");
        }
        final StringBuilder builder = getStringBuilderForHTML(countRow, elements);
        return builder.toString();
    }

    private StringBuilder getStringBuilderForHTML(int[] countRow, Collection<Element> elements) {
        final StringBuilder builder = new StringBuilder(HEADER);
        builder.append("<html>").append("\n")
                .append("<head>").append("\n")
                .append("<title>").append("Элементы").append("</title>").append("\n")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html ; charset=windows-1251\"> \n")
                .append("</head>").append("\n");

        builder.append("<body>").append("\n");

        builder.append("<table border=\"1\" width=\"1000\"> ").append("\n");

        builder.append("<caption>\n").append("Элементы").append("\n</caption>\n");

        builder.append("\n<thead>\n");
        builder.append("<tr>")
                .append("<th>").append("№").append("</th>")
                .append("<th>").append("Название").append("</th>")
                .append("<th>").append("Количесво").append("</th>")
                .append("<th >").append("Обозначения").append("</th>")
                .append("</tr>");

        builder.append("\n</thead>\n");

        builder.append("<tbody>");


        elements.forEach(element ->
                builder.append("<tr>").append("\n")
                        .append("<td>").append(countRow[0]++).append("</td>").append("\n")
                        .append("<td>").append(element.name).append("</td>").append("\n")
                        .append("<td>").append(element.count).append("</td>").append("\n")
                        .append("<td>").append(elementClassToString(element)).append("</td>").append("\n")
                        .append("</tr>").append("\n")
        );


        builder.append("</tbody>");

        builder.append("</table>").append("\n");
        builder.append("</body>").append("\n");
        builder.append("</html>");
        return builder;
    }


    static class Element {
        List<String> elementsClass;
        int count;
        String name;

        Element(String... strings) {
            elementsClass = new ArrayList<>();
            elementsClass.add(strings[0]);
            try {
                count = Integer.valueOf(strings[2].trim());
            }
            catch (ArrayIndexOutOfBoundsException ex){
                Stream.of(strings).forEach(s -> System.out.println("<----"+s+"---->"));
            }
            name = strings[1];
        }

        Element addElement(String[] strings) {
            elementsClass.add(strings[0]);
            count += Integer.valueOf(strings[2].trim());
            name = strings[1];
            return this;
        }
    }

    Collection<Element> getElementsFromFile(String fileName, String... filterParameter) throws IOException {
        Stream<Element> elementStream = getElementsFromFile(fileName).stream();
        return filterBuilder(elementStream, 0, filterParameter).collect(toList());
    }

    String elementClassToString(Element element) {
        return element.elementsClass.stream().collect(joining(",\n"));

    }

    Stream<Element> filterBuilder(Stream<Element> stream, int i, String... str) {
        if (i == str.length)
            return stream;
        else return filterBuilder(stream.filter(element -> element.name.contains(str[i])), i + 1, str);
    }



}
