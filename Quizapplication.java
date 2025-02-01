import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Quizapplication {
    
    private static class Question {
        String questionText;
        String[] options;
        int correctAnswerIndex;

        public Question(String questionText, String[] options, int correctAnswerIndex) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }
    }

    private static final int TIME_LIMIT = 10; // Time limit for each question in seconds
    private static List<Question> quizQuestions = new ArrayList<>();
    private static int score = 0;

    public static void main(String[] args) {
        initializeQuestions();
        takeQuiz();
        displayResults();
    }

    private static void initializeQuestions() {
        quizQuestions.add(new Question("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 2));
        quizQuestions.add(new Question("Which planet is known as the Red Planet?", new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Saturn"}, 1));
        quizQuestions.add(new Question("What is the largest mammal in the world?", new String[]{"1. Elephant", "2. Blue Whale", "3. Giraffe", "4. Great White Shark"}, 1));
        quizQuestions.add(new Question("Who wrote 'Hamlet'?", new String[]{"1. Charles Dickens", "2. Mark Twain", "3. William Shakespeare", "4. Jane Austen"}, 2));
        quizQuestions.add(new Question("What is the chemical symbol for Gold?", new String[]{"1. Au", "2. Ag", "3. Pb", "4. Fe"}, 0));
    }

    private static void takeQuiz() {
        Scanner scanner = new Scanner(System.in);
        Timer timer = new Timer();

        for (int i = 0; i < quizQuestions.size(); i++) {
            Question question = quizQuestions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.questionText);
            for (String option : question.options) {
                System.out.println(option);
            }

            final int currentQuestionIndex = i;
            final boolean[] answered = {false};

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!answered[0]) {
                        System.out.println("\nTime's up! Moving to the next question.\n");
                        answered[0] = true;
                    }
                }
            }, TIME_LIMIT * 1000);

            System.out.print("Your answer (1-" + question.options.length + "): ");
            int userAnswer = scanner.nextInt() - 1; // Convert to zero-based index
            answered[0] = true; // Mark as answered

            if (userAnswer == question.correctAnswerIndex) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Incorrect! The correct answer was: " + question.options[question.correctAnswerIndex] + "\n");
            }

            timer.cancel(); // Cancel the timer for the current question
            timer = new Timer(); // Reset the timer for the next question
        }

        scanner.close();
    }

    private static void displayResults() {
        System.out.println("Quiz completed!");
        System.out.println("Your final score: " + score + "/" + quizQuestions.size());
    }
}
