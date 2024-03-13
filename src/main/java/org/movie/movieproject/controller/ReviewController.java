package org.movie.movieproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.movie.movieproject.dto.ReviewDTO;
import org.movie.movieproject.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// JSON 응답으로 RESTController 어노테이션 사용
@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // url에 담겨 요청 진행된 mno값을 Mapping 진행
    @GetMapping("/{mno}/all")
    // JSON으로 전송은 ResponseEntity로 전송하여 값을 브라우저단에 전달한다
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno")Long mno) {
        List<ReviewDTO> reivewDTOList = reviewService.getListOfMovie(mno);
        return new ResponseEntity<>(reivewDTOList, HttpStatus.OK);
    }
    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO reviewDTO) {
        Long result = reviewService.register(reviewDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modify(@PathVariable("reviewnum") Long reviewnum, @RequestBody ReviewDTO reviewDTO) {
        reviewService.modify(reviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }
    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> remove(@PathVariable("reviewnum")Long reviewnum){
        reviewService.remove(reviewnum);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }
}
