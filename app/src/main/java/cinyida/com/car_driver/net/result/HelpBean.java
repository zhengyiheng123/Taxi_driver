package cinyida.com.car_driver.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/6/19.
 */

public class HelpBean {

    private List<QuestionBean> question;

    public List<QuestionBean> getQuestion() {
        return question;
    }

    public void setQuestion(List<QuestionBean> question) {
        this.question = question;
    }

    public static class QuestionBean {
        /**
         * id : 34
         * title :
         */

        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
