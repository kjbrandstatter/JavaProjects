package tet.ris.game;



//Mike Hubbel
public class Record
{
    private int score;
    private String initials;
    private char[] ABCs = {'a','b','c','d','e','f','e',+
			'g','h','i','j','k','l','m','n','o','p','q',+
			'r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H'+
			'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    public Record()
    {
        score = 0;
        initials = "AAA";
    }
    
    public Record(int newScore, String newInitials)
    {
        score = newScore;
        initials = newInitials;
    }
    
    public String printOutput()
    {
        String str;
        str = score + "," + initials;
        return str;
    }
    
    public String getInitials()
    {
        return initials;
    }
    public void setInitials(String i)
    {
        initials = i;
    }
    public void setInitials(char[] c){
    	String temp = new String(c);
    	setInitials(temp);
    }
    public void setScore(int s)
    {
        score = s;
    }
    public int getScore()
    {
        return score;
    }
    public void print()
    {
        System.out.println(initials + "          " + score);
    }

	public void decrypt(int key) {
		char[] name = getInitials().toCharArray();
		int tempScore = getScore();
		for(int x=0; x<name.length; x++){
			int newChar = getCharNum(name[x]) - key - x;
			while(newChar<0){
				newChar += 52;
			}
			name[x] = ABCs[newChar];
		}
		setInitials(name);
		tempScore = tempScore + (key*1000);// - ((int) Math.pow(2, key));
		setScore(tempScore);
	}

	public void encrypt(int key) {
		char[] name = getInitials().toCharArray();
		int tempScore = getScore();
		for(int x=0; x<name.length; x++){
			int newChar = getCharNum(name[x]) + key + x;
			while(newChar>=52){
				newChar -= 52;
			}
			name[x] = ABCs[newChar];
		}
		setInitials(name);
		tempScore = tempScore - (key*1000);// +((int) Math.pow(2, key));
		setScore(tempScore);
	}
	private int getCharNum(char keyChar) {
		for (int x = 0; x<ABCs.length; x++){
			if (ABCs[x] == keyChar){
				return x;
			}
		}
		return 0;
	}
}
