package com.example.community.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SensitiveWordFilter {
    public static String path ="D:\\java\\project\\DFATest\\src\\words.txt";
    public static WordList wordList;
    private final static char replace= '*';
    private static Set<Character> skip = new HashSet<Character>();
    // 遇到这些字符就会跳过，例如,如果"AB"是敏感词，那么"A B","A=B"也会被屏蔽
    static {
        char[] skipChar = new char[]{'!','*','-','+','_','=',',','.','@'};
        for (Character character:skipChar){
            skip.add(character);
        }
        loadWordFromFile(path);
    }

    public static String sensitiveWordFilter(String text){
        if (wordList==null||wordList.size()==0){
            return text;
        }
        char[] __char__ = text.toCharArray();
        int i,j;
        Word word;
        boolean flag;//用于判断是否为敏感词
        //从文本的某个字符开始检测
        for (i=0;i<__char__.length;i++){
            char c = __char__[i];
            //在敏感词库中查找是否有该字符
            word = wordList.binaryGet(c);
            //存在该字符，从i+1开始检测后续字符
            if (word!=null){
                flag=false;//当前字符尚未检查完，无法判断是否为敏感词
                j=i+1;
                while (j<__char__.length){
                    //包含无关字符，跳过
                    if (skip.contains(__char__[j])){
                        j++;
                        continue;
                    }
                    //敏感词库查询未结束
                    if (word.next != null || word.next.size()!=0){
                        word =word.next.get(__char__[j]);
                        //未查到敏感字符
                        if (word==null){
                            break;
                        }
                        j++;
                    }else {
                        flag = true;
                        break;
                    }
                }
                if (word!=null&&(word.next==null||word.next.size()==0)){
                    flag = true;
                }
                if (flag == true){
                    while (i<j){
                        __char__[i]=replace;
                        i++;
                    }
                    i--;
                }
            }
        }
        return new String(__char__);
    }

    public static void loadWordFromFile(String path){
        String encoding = "UTF-8";
        File file = new File(path);
        try {
            if (file.isFile()&&file.exists()){
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                ArrayList<String> list = new ArrayList<>();
                while ((line = bufferedReader.readLine())!=null){
                    list.add(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                loadWord(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadWord(ArrayList<String> words){
        if (words==null) return;
        char[] chars;
        WordList now;
        Word word;
        wordList = new WordList();
        for (String __word__ : words){
            if (__word__ ==null){
                continue;
            }
            chars = __word__.toCharArray();
            now = wordList;
            //？？
            word = wordList.get(chars[0]);
            int i=0;
            int length =chars.length;
//            do {
//                if (word!=null){
//                    if (word.next ==null){
//                        word.next = new WordList();
//                    }
//                    now = word.next;
//                    word = now.get(chars[i]);
//                }
//                if (word==null){
//                    word = now.add(chars[i]);
//                }
//                i++;
//            }while (i<length);
            do {
                if (now!=wordList){
                    word = now.get(chars[i]);
                }
                if (word == null){
                    word = now.add(chars[i]);
                    if (word.next==null){
                        word.next = new WordList();
                    }
                    now = word.next;
                }else if (word!= null){
                    if (word.next==null){
                        word.next = new WordList();
                    }
                    now = word.next;
                }
                i++;
            }while (i<length);
        }
        sort(wordList);
    }

    public static void sort(WordList wordList){
        if (wordList==null) return;
        Collections.sort(wordList);
        for (Word word:wordList){
            sort(word.next);
        }
    }
}

class WordList extends ArrayList<Word> {
    public Word get(char c){
        for (Word w:this){
            if (w.c==c){
                return w;
            }
        }
        return null;
    }

    public Word binaryGet(char c){
        int left,right,key;
        Word w;
        left =0;
        right=this.size()-1;
        while (left<=right){
            key=(left+right)/2;
            w = get(key);//？？
            if (w.c==c){
                return w;
            }else if (w.c>c){
                right=key-1;
            }else {
                left=key+1;
            }
        }
        return null;
    }
    public Word add(char c){
        Word word =new Word(c);
        this.add(word);
        return word;
    }
}

class Word implements Comparable<Word>{
    public char c;
    public WordList next = null;

    public Word(char c) {
        this.c = c;
    }

    @Override
    public int compareTo(Word word) {
        return c-word.c;
    }
}
