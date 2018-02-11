package com.mm.dev.service;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class MenuTest extends BaseTest{

	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser=new JsonParser();//创建解析器
        JsonObject object=(JsonObject)parser.parse(new FileReader("src/main/resources/doc/menu.json"));
		System.out.println(object);
    }  
}
