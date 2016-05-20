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
        TransformerListOfElementsToNeedFormat transformerListOfElementsToNeedFormat
                = new TransformerListOfElementsToNeedFormat();

        byte[] bytes =
                transformerListOfElementsToNeedFormat
                      .convertElementsToHTML
                              ("D:\\TransformListOfElementsToSpecefication\\src\\main\\resources\\SFM-4A250.txt")

                        .getBytes(Charset.forName("windows-1251"));
        Files.write(Paths.get("D:\\TransformListOfElementsToSpecefication\\src\\main\\resources\\SFM-4A250.html"), bytes);
    }
}

