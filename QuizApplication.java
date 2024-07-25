import java.util.*;
import java.util.concurrent.*;

class Question {
    String questionText;
    String[] options;
    int correctAnswer;

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(questionText).append("\n");
        for (int i = 0; i < options.length; i++) {
            sb.append((i + 1)).append(". ").append(options[i]).append("\n");
        }
        return sb.toString();
    }
}

public class QuizApplication {
    private static List<Question> questions = new ArrayList<>();
    private static Map<Integer, Boolean> results = new HashMap<>();
    private static int score = 0;
    private static final int TIME_LIMIT = 10; // Time limit in seconds

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Adding sample questions
        questions.add(new Question("What is the capital of France?", new String[]{"Berlin", "Paris", "Rome", "Madrid"}, 2));
        questions.add(new Question("What is the largest planet in our solar system?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 3));
        questions.add(new Question("Who wrote 'To Kill a Mockingbird'?", new String[]{"Harper Lee", "Mark Twain", "F. Scott Fitzgerald", "Ernest Hemingway"}, 1));

        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ":");
            Question question = questions.get(i);
            System.out.println(question);

            boolean answered = false;
            int answer = 0;

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return scanner.nextInt();
                }
            });

            try {
                answer = future.get(TIME_LIMIT, TimeUnit.SECONDS);
                answered = true;
            } catch (TimeoutException e) {
                System.out.println("Time's up! Moving to the next question.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            } finally {
                executor.shutdownNow();
            }

            if (answered && question.isCorrect(answer)) {
                score++;
                results.put(i, true);
            } else {
                results.put(i, false);
            }
        }

        displayResults();
        scanner.close();
    }

    private static void displayResults() {
        System.out.println("\nQuiz Finished!");
        System.out.println("Your final score: " + score + "/" + questions.size());
        System.out.println("\nSummary:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ": " + questions.get(i).questionText);
            System.out.println("Your answer: " + (results.get(i) ? "Correct" : "Incorrect"));
        }
    }
}
