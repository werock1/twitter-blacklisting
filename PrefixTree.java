package tweetss;
public class PrefixTree
{
    static TrieNode tree;
    public PrefixTree()
    {
           tree = createTree();
    }
    static TrieNode createTree()
    {
        return(new TrieNode(' ', false));
    }

    static void insertWord(TrieNode root, String word)
    {

        int offset = 97;
        int l = word.length();
        char[] letters = word.toCharArray();
        TrieNode curNode = root;
       // System.out.print(curNode);
        for (int i = 0; i < l; i++)
        {
            if (curNode.links[letters[i]-offset] == null)
                curNode.links[letters[i]-offset] = new TrieNode(letters[i], i == l-1 ? true : false);
            curNode = curNode.links[letters[i]-offset];
         //   System.out.println(letters[i]);
        }
    }

    static boolean find(TrieNode root, String word)
    {
        char[] letters = word.toCharArray();
        int l = letters.length;
        int offset = 97;
        TrieNode curNode = root;

        int i;
        for (i = 0; i < l; i++)
        {
            if (curNode == null)
                return false;
            curNode = curNode.links[letters[i]-offset];
        }

        if (i == l && curNode == null)
            return false;

        if (curNode != null && !curNode.fullWord)
            return false;

        return true;
    }

    static void printTree(TrieNode root, int level, char[] branch)
    {
        if (root == null)
            return;

        for (int i = 0; i < root.links.length; i++)
        {
            branch[level] = root.letter;
            printTree(root.links[i], level+1, branch);
        }

        if (root.fullWord)
        {
            for (int j = 1; j <= level; j++)
                System.out.print(branch[j]);
            System.out.println();
        }
    }

    public  void insert(String word)
    {
        insertWord(tree, word);
    }
    public int findw(String s)
    {
        if(find(tree,s))
           return 1;
        else
            return 0;

    }
}

class TrieNode
{
    char letter;
    TrieNode[] links;
    boolean fullWord;

    TrieNode(char letter, boolean fullWord)
    {
        this.letter = letter;
        links = new TrieNode[26];
        this.fullWord = fullWord;
    }
}
