import java.util.*;
import java.io.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
//import jdk.nashorn.internal.parser.JSONParser;
import org.json.*;
//import org.json.simple.parser.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Modify {
/*
    static void parsing(JSONObject root)
    {
        String key;
        Iterator<String> iterator = root.keys();

        while (iterator.hasNext()) {
            key =iterator.next();

           // System.out.println(key);

          //  Object val_newer = jo.get(key);
            if(root.isNull(key)) {
               // JSONPut (root,key,val_newer);
                continue;
            }

            Object val_older = root.get(key);
            if((val_older instanceof JSONObject))
            {
                parsing((JSONObject) val_older);
            }
            else
            {
                if(val_older instanceof Integer)
                    System.out.println("key: "+key+"\tType: Integer");
                else if(val_older instanceof Long)
                    System.out.println("key: "+key+"\tType: Long");
                else if(val_older instanceof Boolean)
                    System.out.println("key: "+key+"\tType: Boolean");
                else if(val_older instanceof String)
                    System.out.println("key: "+key+"\tType: String");
                else if(val_older instanceof JSONObject)
                    System.out.println("key: "+key+"\tType: JSONObject");
                else if(val_older instanceof JSONArray)
                    System.out.println("key: "+key+"\tType: JSONArray");
             //   JSONPut(root , key,val_newer);
            }
        }
    }


    public static void main(String[] args) {
        try {
            FileInputStream inFile = new FileInputStream("src/main/java/Template.json");
            byte[] str = new byte[inFile.available()];
            inFile.read(str);
            String text = new String(str);
            JSONObject root = new JSONObject(text);
            parsing(root);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

/*    private static JSONObject createJSONObject(String jsonString){
        JSONObject  jsonObject=new JSONObject();
        JSONParser jsonParser=new  JSONParser();
        if ((jsonString != null) && !(jsonString.isEmpty())) {
            try {
                jsonObject=(JSONObject) jsonParser.parse(jsonString);
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }*/
 /*   public static void JSONPut(JSONObject json, String key, Object val_newer)
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
            else if(!((val_newer instanceof JSONObject) || !(val_older instanceof JSONObject)
                        || !(val_newer.equals(val_older))))
            {
                JSONPut(root , key,val_newer);
            }
        }
    }
//root.put(key,val_newer);
    public static void main(String[] args) throws IOException , JSONException, IOException
    {
      /*  FileInputStream inFile = new FileInputStream("src/main/java/Template.json");
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


       */

  /*
        while (iterator.hasNext()) {
            key =iterator.next();

            System.out.println(key);



            String val_newer = (String) jo.get(key);

            String val_older = (!root.isNull(key))?root.getString(key):null;
            if(!val_newer.equals(val_older))
            {
                root.put(key,val_newer);

            }
        }


        */
 /*
        String key = "JSON";
        String key1 = "1";
        String key2 = "2";
        
        if(root.isNull(key)) {
            System.out.println("This key is not found");
            return;
        }
        JSONArray arr = root.getJSONArray(key);
        System.out.println(root.getJSONArray(key));

        for(int i=0;i<arr.length();i++) {
            Object aObj=arr.get(i);
            if(aObj instanceof String)
            {
                System.out.println(aObj + " is string");
            }
            else if (aObj instanceof JSONObject)
            {
                System.out.println(aObj + " is JSON");
            }
        }



        Object Obj = root.get(key);
        Object a = root;
        System.out.println(a instanceof JSONObject);
        if(!(Obj instanceof JSONObject)) {
            Obj = (JSONObject) ((JSONObject) Obj).get(key1);
        }
        if(Obj instanceof JSONObject) {
            Obj = (JSONObject) ((JSONObject) Obj).get(key2);
        }
        JSONPut((JSONObject) Obj);

        System.out.println(root);

    }
*/

/*        ObjectMapper mapper = new ObjectMapper();
        String key;

       // JSONObject jo = new JSONObject("{key1:\"val1\", key2:\"val2\"}");
        //Read from file
        FileInputStream inFile = new FileInputStream("src/main/java/override.json");
        byte[] str = new byte[inFile.available()];
        inFile.read(str);
        String text = new String(str);
        JSONObject root = new JSONObject(text);

        inFile = new FileInputStream("src/main/java/Template.json");
        str = new byte[inFile.available()];
        inFile.read(str);
        text = new String(str);
        JSONObject jo = new JSONObject(text);

//        JSONObject root = mapper.readValue(new File("C:\\JBDL-3-master\\JsonModificaton\\src\\main\\java\\Template.json"), JSONObject.class);
//        JSONObject jo = mapper.readValue(new File("C:\\JBDL-3-master\\JsonModificaton\\src\\main\\java\\override.json"), JSONObject.class);

        Iterator<String> iterator = jo.keys();

        while (iterator.hasNext()) {
            key =iterator.next();

            System.out.println(key);

            String val_newer = jo.getString(key);

            String val_older = (!root.isNull(key))?root.getString(key):null;
            if(!val_newer.equals(val_older))
            {
                root.put(key,val_newer);

            }
        }


        //Write into the file
        try (FileWriter file = new FileWriter("src/main/java/target.json"))
        {
            file.write(root.toString());
            System.out.println("Successfully updated json object to file...!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
