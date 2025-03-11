package by.agsr.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class PaginationPage<T> {

    Integer currentPage;
    Integer maxPage;
    Integer numFirstElement;
    Integer numLastElement;
    Integer size;
    Long totalCount;

    List<T> content = new ArrayList<>();

    public PaginationPage(Integer currentPage, Integer size, Long totalCount) {
        this.currentPage = currentPage;
        this.numFirstElement = (currentPage - 1) * size + 1;
        this.numLastElement = (int) Math.min((this.numFirstElement + size - 1), totalCount);
        this.size = size;
        this.totalCount = totalCount;
        this.maxPage = (int) Math.ceil((double) totalCount / (double) size);
    }
}
