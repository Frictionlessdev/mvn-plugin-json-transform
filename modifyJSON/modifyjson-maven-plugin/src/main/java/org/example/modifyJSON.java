package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.json.*;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

//import org.json.simple.parser.ParseException;


@Mojo(name="modifyJSON", defaultPhase = LifecyclePhase.COMPILE)
public class modifyJSON extends AbstractMojo {
    public static void JSONPut(JSONObject json, String key, Object val_newer)
    {
        json.put(key,val_newer);
    }

    static void parsing(JSONObject root, JSONObject jo)
    {
        String key;
        Iterator<String> iterator = jo.keys();

        while (iterator.hasNext()) {
            key =iterator.next();

            System.out.println(key);

            Object val_newer = jo.get(key);
            if(root.isNull(key)) {
                JSONPut (root,key,val_newer);
                continue;
            }

            Object val_older = root.get(key);
            if((val_newer instanceof JSONObject) && (val_older instanceof JSONObject))
            {
                parsing((JSONObject) val_older,(JSONObject) val_newer);
            }
            else if(!((val_newer instanceof JSONObject) || (val_older instanceof JSONObject)
                    || (val_newer.equals(val_older))))
            {
                JSONPut(root , key,val_newer);
            }
        }
    }
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            FileInputStream inFile = null;

            inFile = new FileInputStream("src/main/java/Template.json");

            byte[] str = new byte[inFile.available()];
            inFile.read(str);
            String text = new String(str);
            JSONObject root = new JSONObject(text);

            inFile = new FileInputStream("src/main/java/override.json");
            str = new byte[inFile.available()];
            inFile.read(str);
            text = new String(str);
            JSONObject jo = new JSONObject(text);

            parsing(root,jo);

            System.out.println(root);
            try (FileWriter file = new FileWriter("src/main/java/target.json"))
            {
                file.write(root.toString());
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
        }
    }
}