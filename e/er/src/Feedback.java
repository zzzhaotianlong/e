public class Feedback {
    private String firstName;
    private String lastName;
    private String email;
    private String completeFeedback;
    private String reviewID;
    private boolean longFeedback;

    public Feedback(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void analyseFeedback(boolean isConcatenation, String sent1, String sent2, String sent3, String sent4, String sent5) {
        if (isConcatenation) {
            completeFeedback = feedbackUsingConcatenation(sent1, sent2, sent3, sent4, sent5);
        } else {
            completeFeedback = feedbackUsingStringBuilder(sent1, sent2, sent3, sent4, sent5).toString();
        }
        longFeedback = checkFeedbackLength(completeFeedback);
        reviewID = createReviewID(firstName, lastName, completeFeedback);
    }

    private String feedbackUsingConcatenation(String s1, String s2, String s3, String s4, String s5) {
        String concatenatedFeedback = s1 + s2 + s3 + s4 + s5;
        return concatenatedFeedback;
    }

    private StringBuilder feedbackUsingStringBuilder(String s1, String s2, String s3, String s4, String s5) {
        StringBuilder sb = new StringBuilder();
        sb.append(s1);
        sb.append(s2);
        sb.append(s3);
        sb.append(s4);
        sb.append(s5);
        return sb;
    }

    private boolean checkFeedbackLength(String feedback) {
        return feedback.length() > 500;
    }

    private String createReviewID(String first, String last, String feedback) {
        String namePart = (first + last).substring(2, 6).toUpperCase();
        String feedbackPart = feedback.substring(10, 15).toLowerCase();
        String lengthPart = feedback.length() + "_";
        String timePart = String.valueOf(System.currentTimeMillis());
        String fullID = namePart + feedbackPart + lengthPart + timePart;
        fullID = fullID.replace(" ", "");
        return fullID;
    }

     @Override
    public String toString() {
        return "First Name: " + firstName + "\n" +
               "Last Name: " + lastName + "\n" +
               "Email: " + email + "\n" +
               "Complete Feedback: " + completeFeedback + "\n" +
               "Is Long Feedback: " + longFeedback + "\n" +
               "Review ID: " + reviewID;
    }
}


