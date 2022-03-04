package jp.learningpark.modules.guard.dto;

import java.util.List;

public class F30301Dto {
    // OK または NG
    String status;
    // エラーメッセージ
    String error;
    // エラーコード
    String error_code;
    // 実際に適用された記事件数
    List<F30301ContentsDto> contents;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public List<F30301ContentsDto> getContents() {
        return contents;
    }

    public void setContents(List<F30301ContentsDto> contents) {
        this.contents = contents;
    }
}
