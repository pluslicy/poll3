package com.briup.apps.poll.bean;

public class SurveyLog {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column poll_survey_log.id
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column poll_survey_log.ip
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    private String ip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column poll_survey_log.survey_id
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    private Long surveyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column poll_survey_log.survey_date
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    private String surveyDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column poll_survey_log.id
     *
     * @return the value of poll_survey_log.id
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column poll_survey_log.id
     *
     * @param id the value for poll_survey_log.id
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column poll_survey_log.ip
     *
     * @return the value of poll_survey_log.ip
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column poll_survey_log.ip
     *
     * @param ip the value for poll_survey_log.ip
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column poll_survey_log.survey_id
     *
     * @return the value of poll_survey_log.survey_id
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    public Long getSurveyId() {
        return surveyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column poll_survey_log.survey_id
     *
     * @param surveyId the value for poll_survey_log.survey_id
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column poll_survey_log.survey_date
     *
     * @return the value of poll_survey_log.survey_date
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    public String getSurveyDate() {
        return surveyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column poll_survey_log.survey_date
     *
     * @param surveyDate the value for poll_survey_log.survey_date
     *
     * @mbg.generated Fri Nov 16 10:06:41 CST 2018
     */
    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate == null ? null : surveyDate.trim();
    }
}