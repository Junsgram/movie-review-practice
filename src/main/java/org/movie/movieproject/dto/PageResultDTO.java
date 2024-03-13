package org.movie.movieproject.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    // DTO List field
    private List<DTO> dtoList;
    // page number
    private int totalPage;
    // List Size
    private int size;
    // Current Page
    // Page
    private int page;
    // Start, End Page Number
    private int start, end;
    // prev, next
    private boolean prev, next;
    // Page Number List(페이지 목록 번호)
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        int tempEnd = (int) (Math.ceil(page/10.0))*10;
        start = tempEnd -9;
        end = totalPage -9;

        prev = start > 1;
        next = totalPage > tempEnd;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
