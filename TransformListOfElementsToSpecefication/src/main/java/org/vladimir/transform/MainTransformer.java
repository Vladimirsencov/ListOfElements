package org.vladimir.transform;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Sentsov on 12.05.2016.
 */
public class MainTransformer {
    public static void main(String[] args) throws IOException {
        TransformerListOfElementsToNeedFormat transformerListOfElementsToNeedFormat = new TransformerListOfElementsToNeedFormat();
        /*Collection<TransformerListOfElementsToNeedFormat.Element> elements =
                transformerListOfElementsToNeedFormat
                        .getElementsFromFile
                                ("D:\\TransformListOfElementsToSpecfication\\src\\main\\resources\\SVP-564.txt");*/

        byte[] bytes =
              transformerListOfElementsToNeedFormat
                      .convertElementsToHTML
                              ("D:\\TransformListOfElementsToSpecefication\\src\\main\\resources\\SVP-564.txt")
                      .getBytes(Charset.forName("UTF-8"));
        Files.write(Paths.get("D:\\TransformListOfElementsToSpecefication\\src\\main\\resources\\564.html"), bytes);
    }
}

