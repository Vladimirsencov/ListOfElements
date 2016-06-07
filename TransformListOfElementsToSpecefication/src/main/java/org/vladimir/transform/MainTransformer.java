package org.vladimir.transform;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Sentsov on 12.05.2016.
 */
public class MainTransformer {
    final static String[] firms = {

    };
    public static void main(String[] args) throws IOException {
        System.out.println(args.length);
        char ch = '0';
        while (ch<9){
            System.out.println(ch++);
        }
        TransformerListOfElementsToNeedFormat transformerListOfElementsToNeedFormat
                = new TransformerListOfElementsToNeedFormat();

        byte[] bytes =
                transformerListOfElementsToNeedFormat
                      .convertElementsToHTML
                              ("D:\\ListOfElements\\TransformListOfElementsToSpecefication\\src\\main\\resources\\SVR-525.txt")

                        .getBytes(Charset.forName("windows-1251"));
        Files.write(Paths.get("D:\\ListOfElements\\TransformListOfElementsToSpecefication\\src\\main\\resources\\SVR-525.html"), bytes);

        System.out.println(checkString("Конденсатор 0402-X7R-16В-0.1±15% Murata","Конденсатор 0402-X7R-16В-0.1±15% Murata"));
    }

    private static int checkString(String...strings){
        if(strings[0].equals(strings[1])) return -2;
        String s1= strings[0];
        String s2 = strings[1];
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        for (int i = 0; i < chars1.length ; i++) {
            if(chars1[i]!=chars2[i]) return i;
        }
        return -1;
    }
}

