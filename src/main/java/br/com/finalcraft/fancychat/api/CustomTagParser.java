package br.com.finalcraft.fancychat.api;

import br.com.finalcraft.fancychat.EverNifeFancyChat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CustomTagParser {

    private HashMap<String, String> map = new HashMap<>();

    public CustomTagParser() throws IOException {
        File file = new File("plugins/EverNifeFancyChat/customtags.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        if(file.exists()){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line = bufferedReader.readLine();
            while (line != null){

                String[] ssplited = line.split(":");

                if(ssplited.length == 2){

                    if(ssplited[1].charAt(0) == ' '){
                        map.put(ssplited[0], ssplited[1].substring(1).replace("&","\u00a7"));
                    }else{
                        map.put(ssplited[0], ssplited[1].replace("&","\u00a7"));
                    }
                }

                line = bufferedReader.readLine();
            }
        }

        if(!map.isEmpty()){
            EverNifeFancyChat.info("Carregado " + map.size() + " CustomTags.");
        }
    }

    public HashMap<String, String> getMap(){
        return map;
    }

    public String replace(String string){

        if(map.isEmpty()) return string;

        String base = string;

        String last = "";
        for(String s : string.split(" ")){
            if(map.containsKey(s) && !last.equals(s)){
                base = base.replace(s, map.getOrDefault(s, s));
                last = s;
            }
        }

        System.out.println(base);
        return base;
    }

}
