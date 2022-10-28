import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Node{
    private HashMap<Character, Node> dictChar = new HashMap<>();

    public void add(char c){
        this.dictChar.put(c,new Node());
    }

    public boolean contains(char c){
        return this.dictChar.containsKey(c);
    }

    public Node get(char c){
        return this.dictChar.get(c);
    }

    public HashMap<Character, Node> getDictChar(){
        return new HashMap<>(this.dictChar);
    }
}


public class MyTree {

    private Node root = new Node();

    public static void main(String[] args) throws IOException{

        System.out.println("Sözlük Yükleniyor. Lütfen Bekleyin...");

        List<String> dictionary = new ArrayList<>();
        MyTree tree = new MyTree();
        BufferedReader sozluk = new BufferedReader(new FileReader(args[0]));
        
        for (String currentWord = sozluk.readLine(); currentWord != null; currentWord = sozluk.readLine()){
            tree.insert(currentWord);
            dictionary.add(currentWord);
        }
        sozluk.close();
        System.out.println("Sözlük Yüklendi.");

        Scanner reader = new Scanner(System.in);
        while(true){
        System.out.println("Bir Kelime Yazıp Enter Tuşuna Basınız");
        String word = reader.nextLine();
        System.out.println("Olası Kelimeler:");

        for(String guess : tree.autoComplete(word, dictionary)) System.out.println(guess);}

    }

    public void insert(String word){
        insert(word, root);
    }

    private void insert(String word, Node node) {
        if(word == null || word.isEmpty()) return;
        char first = word.charAt(0);
        if(!node.contains(first)) node.add(first);
        insert(word.substring(1), node.get(first));
    }

    public boolean search(String word, List<String> dictionary){
        return dictionary.contains(word);
    }

    public List<String> autoComplete(String prefix, List<String> dictionary){
        List<String> words = new ArrayList<>();
        if(prefix == null || prefix.isEmpty()) return words;
        return autoComplete(prefix, words, root, prefix, dictionary);
    }

    private List<String> autoComplete(String prefix, List<String> words, Node node, String original, List<String> dictionary) {
        if(prefix == null || prefix.isEmpty()) return completeWord(original, node, words, dictionary);
        char first = prefix.charAt(0);
        if(!node.contains(first)) return words;
        return autoComplete(prefix.substring(1), words, node.get(first), original, dictionary);
    }

    private List<String> completeWord(String original, Node node, List<String> words, List<String> dictionary) {
        if((node.getDictChar().size() == 0) || search(original, dictionary)) words.add(original);
        node.getDictChar().forEach((c, subNode) -> {completeWord(original + c, subNode, words, dictionary);});
        return words;
    }

}



