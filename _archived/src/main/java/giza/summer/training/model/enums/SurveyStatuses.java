package giza.summer.training.model.enums;

public enum SurveyStatuses {
    New(1L), IN_Progress(2L), Closed(3L);
    private final Long id;

    SurveyStatuses(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }
}
