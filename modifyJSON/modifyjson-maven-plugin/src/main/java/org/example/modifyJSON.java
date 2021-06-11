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
import java.util.ArrayList;
import java.util.Iterator;

//import org.json.simple.parser.ParseException;


@Mojo(name="modifyJSON", defaultPhase = LifecyclePhase.COMPILE)
public class modifyJSON extends AbstractMojo {
/*    public static void JSONPut(JSONObject json, String key, Object val_newer)
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


 */
    public static void JSONPut(JSONObject json, String key, Object val_newer)
    {
        json.put(key,val_newer);
    //System.out.println("PUTCALLED");
    }


    static void parsing(JSONObject root, JSONObject jo, ArrayList<String> path) throws IOException {
        String key;
        Iterator<String> iterator = jo.keys();
        //System.out.println(path);
        FileInputStream inFile = new FileInputStream("src/main/java/ArrayStructure.json");
        byte[] str = new byte[inFile.available()];
        inFile.read(str);
        String text = new String(str);
        JSONObject arrayStructure = new JSONObject(text);
        JSONArray array=arrayStructure.getJSONArray("Array");
        while (iterator.hasNext()) {
            key =iterator.next();
            Object val_newer = jo.get(key);
            if(root.isNull(key)) {
                //JSONPut (root,key,val_newer);
                System.out.println("ERROR");
                continue;
            }

            Object val_older = root.get(key);
            if((val_older instanceof JSONObject) && (val_newer instanceof JSONObject))
            {
                path.add(key);
                //System.out.println(key);
                parsing((JSONObject) val_older,(JSONObject) val_newer,path);
            }
            else
            {
                path.add(key);
                if((val_older instanceof Integer) && (val_newer instanceof Integer))
                    JSONPut(root,key,val_newer);
                else if((val_older instanceof Long) && (val_newer instanceof Long))
                    JSONPut(root,key,val_newer);
                else if((val_older instanceof Boolean) && (val_newer instanceof Boolean))
                    JSONPut(root,key,val_newer);
                else if((val_older instanceof String) && (val_newer instanceof String))
                    JSONPut(root,key,val_newer);

                else if((val_older instanceof JSONArray) && (val_newer instanceof JSONArray)) {
                    //System.out.println("ARR");
                    String imp_field = "";
                    for(int i=0;i<array.length();i++){
                        JSONObject structure = array.getJSONObject(i);
                        if(key.equals(structure.getString("key"))) {
                            JSONArray arrayPath = structure.getJSONArray("path");
                            ArrayList<Object> arrayPath2 = ArrayUtil.convert(arrayPath);
                            if(arrayPath2.equals(path)) {
                                if(structure.getString("value_type").equals("JSONObject"))
                                    imp_field = structure.getString("importatnt_field");
                                System.out.println(structure);
                                break;
                            }
                        }
                    }
                    if(!imp_field.isEmpty())
                    {
                        ArrayList<Object> arr_older =ArrayUtil.convert((JSONArray) val_older);
                        ArrayList<Object> arr_newer =ArrayUtil.convert((JSONArray) val_newer);
                        for(int i=0;i< arr_newer.size();i++)
                        {
                            int flag=0;
                            JSONObject newer = ((JSONArray) val_newer).getJSONObject(i);
                            for(int j=0;j<arr_older.size();j++)
                            {
                                JSONObject old = ((JSONArray) val_older).getJSONObject(j);

                                if((old.get(imp_field)).equals(newer.get(imp_field)))
                                {
                                    flag=1;
                                    parsing(old,newer,path);
                                }

                            }
                            if(flag==0)
                            {

                                // JSONArray valn=(JSONArray)val_newer;
                                //int size=((JSONArray) val_newer).length();
                                //System.out.println("*********"+ ((JSONArray) val_newer).getJSONObject(i).get(imp_field));
                                ((JSONArray) val_older).put(newer);
                                //ArrayList<Object> new_arr = ArrayUtil.convert(val_newer)
                            }
                        }

                    }
                    System.out.println("---------------------------------------------\n" +
                            val_older+"\n--------------------------------\n");
                    //System.out.println("Path " + path + "\t" + "key: " + key + "\tType: JSONArray" + "\n");

                }
                else
                {
                    System.out.println("mismatch key " + key);
                    if(val_newer instanceof JSONArray)
                        System.out.println("newer");
                    if(val_older instanceof JSONArray)
                        System.out.println("older");

                }
                //   JSONPut(root , key,val_newer);
            }
            System.out.println("Path " + path + "\t" + "key: " + key + "\tType: JSONArray" + "\n");

            int n=path.size();
            path.remove(n-1);
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

            ArrayList<String> path = new ArrayList<>();
            parsing(root,jo,path);

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