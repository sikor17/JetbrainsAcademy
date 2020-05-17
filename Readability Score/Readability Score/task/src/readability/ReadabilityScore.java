package readability;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadabilityScore {

    double scoreAutomatedReadabilityIndex;
    String ageAutomatedReadabilityIndex;
    AutomatedReadabilityIndex ari = new AutomatedReadabilityIndex();
    FleschKincaid fk = new FleschKincaid();
    SimpleMeasureOfGobbledygook smog = new SimpleMeasureOfGobbledygook();
    ColemanLiauIndex cli = new ColemanLiauIndex();
    double scoreFleschKincaid;
    double scoreSimpleMeasureOfGobbledygook;
    double scoreColemanLiauIndex;
    double averageScore;
    String text;
    String[] stringsOfText;
    List<Character> listOfCharacters = new ArrayList<>();
    List<String> listOfWords = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    double characters;
    double words;
    double sentences;
    double syllables;
    double polysyllables;
    private String ageFleschKincaid;
    private String ageSimpleMeasureOfGobbledygook;
    private String ageColemanLiauIndex;


    void importFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        String output = "";
        if (!file.exists()) {
            System.out.println("File not found.");
        } else {
            Scanner scanFile = new Scanner(new File(filePath));
            while (scanFile.hasNext()) {
                text = scanFile.nextLine();
            }
            scanFile.close();
        }
    }

    public void processTheText() {
        stringsOfText = text.split("[.!?]+");
        String[] stringOfWords;
        char[] character = text.toCharArray();

        for (char ch : character) {
            if (ch != ' ') {
                listOfCharacters.add(ch);
            }
        }

        for (String item : stringsOfText) {
            stringOfWords = item.split("[^\\w,]");
            for (String t : stringOfWords) {
                if (!t.isEmpty()) {
                    listOfWords.add(t);
                }
            }
        }
        characters = listOfCharacters.size();
        words = listOfWords.size();
        sentences = stringsOfText.length;
        syllables = 0;
        polysyllables = 0;

        calculateScore();
        calculateAge();
    }

    public void calculateScore() {
        scoreAutomatedReadabilityIndex =
                ari.getScoreAutomatedReadabilityIndex(characters, words, sentences);
        scoreFleschKincaid = fk.getScoreFleschKincaid(words, sentences, syllables);
        scoreSimpleMeasureOfGobbledygook = smog.getScoreSimpleMeasureOfGobbledygook(polysyllables, sentences);
        scoreColemanLiauIndex = cli.getScoreColemanLiauIndex(characters, words, sentences);
    }

    public void calculateAge() {
        ageAutomatedReadabilityIndex = getAgeIndex(scoreAutomatedReadabilityIndex);
        ageFleschKincaid = getAgeIndex(scoreFleschKincaid);
        ageSimpleMeasureOfGobbledygook = getAgeIndex(scoreSimpleMeasureOfGobbledygook);
        ageColemanLiauIndex = getAgeIndex(scoreColemanLiauIndex);
    }

    String getAgeIndex(double score) {
        String age = "";
        switch ((int) Math.round(score)) {
            case 1:
                age = "6";
                break;
            case 2:
                age = "7";
                break;
            case 3:
                age = "9";
                break;
            case 4:
                age = "10";
                break;
            case 5:
                age = "11";
                break;
            case 6:
                age = "12";
                break;
            case 7:
                age = "13";
                break;
            case 8:
                age = "14";
                break;
            case 9:
                age = "15";
                break;
            case 10:
                age = "16";
                break;
            case 11:
                age = "17";
                break;
            case 12:
                age = "18";
                break;
            case 13:
                age = "24";
                break;
            case 14:
                age = "24+";
                break;
        }
        return age;
    }

    public void printSummary() {
        System.out.println("The text is: \n" + text);
        System.out.println();
        System.out.println("Words: " + listOfWords.size());
        System.out.println("Sentences: " + stringsOfText.length);
        System.out.println("Characters: " + listOfCharacters.size());
        System.out.println("Syllables: ");
        System.out.println("Polysyllables: ");
    }

    public void calculateReadabilityScore(String inputScore) {
        switch (inputScore) {
            case "ARI":
                printARI();
                break;
            case "FK":
                printFK();
                break;
            case "SMOG":
                printSMOG();
                break;
            case "CL":
                printCL();
                break;
            case "all":
                printAll();
                break;
        }
        System.out.println();
        System.out.printf("This text should be understood by %s year olds.\n", averageScore);
    }

    private void printAll() {
        printARI();
        printFK();
        printSMOG();
        printCL();
        averageScore = (Double.parseDouble(ageAutomatedReadabilityIndex) + Double.parseDouble(ageFleschKincaid) +
                Double.parseDouble(ageSimpleMeasureOfGobbledygook) + Double.parseDouble(ageColemanLiauIndex)) / 4;
    }

    public void printCL() {
        System.out.printf("Coleman–Liau index: %.2f (about %s year olds).\n",
                scoreColemanLiauIndex, ageColemanLiauIndex);
        averageScore = Double.parseDouble(ageColemanLiauIndex);
    }

    public void printSMOG() {
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s year olds).\n",
                scoreSimpleMeasureOfGobbledygook, ageSimpleMeasureOfGobbledygook);
        averageScore = Double.parseDouble(ageSimpleMeasureOfGobbledygook);
    }

    public void printFK() {
        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s year olds).\n",
                scoreFleschKincaid, ageFleschKincaid);
        averageScore = Double.parseDouble(ageFleschKincaid);
    }

    public void printARI() {
        System.out.printf("Automated Readability Index: %.2f (about %s year olds).\n",
                scoreAutomatedReadabilityIndex, ageAutomatedReadabilityIndex);
        averageScore = Double.parseDouble(ageAutomatedReadabilityIndex);
    }

    public String askForAlgorithm() {
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        return scanner.next();
    }

}
