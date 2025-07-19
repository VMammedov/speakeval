package org.project.speakeval.constants;

public class Constants {

    private Constants() {
        throw new IllegalStateException("No Instance!");
    }

    public static final String TRACE_ID_NAME = "trace_id";

    public static final class HttpResponseConstants {
        public static final String STATUS = "status";
        public static final String MESSAGE = "message";
        public static final String PATH = "path";
        public static final String ERROR = "error";
        public static final String ERRORS = "errors";
        public static final String KEY = "key";
        public static final String TIMESTAMP = "timestamp";

        private HttpResponseConstants() {
            throw new IllegalStateException("No Instance!");
        }
    }

    public static final class RelatedEntityType {
        public static final String QUESTION = "Question";
    }

    public static final class BlobNamingFormat {
        public static final String SESSION_AUDIO_ANSWER_FORMAT = "sessions/%s/q-%s-%03d.%s";
        public static final String EXAM_IMAGE_QUESTION_FORMAT = "exam-image/%s/i-%s.%s";
    }
}
