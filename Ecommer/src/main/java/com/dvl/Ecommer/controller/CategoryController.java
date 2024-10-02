package com.dvl.Ecommer.controller;

import com.dvl.Ecommer.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")

public class CategoryController {

    // hien thi tat ca categories
    @GetMapping("") // http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getAllCategories(
            @RequestParam("Page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("getAllcategories,page = %d,limit =%d", page, limit));
    }

    @PostMapping("")
    // nếu tham số truyền vòa là 1 object thi sao  ?  => Request Object
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {

        // Kiểm tra nếu có lỗi xác thực
        if (result.hasErrors()) {
            // Lấy danh sách thông báo lỗi dưới dạng chuỗi
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()) // Properly convert error to String
                    .toList();

            // Trả về phản hồi lỗi bad request kèm theo các thông báo lỗi
            return ResponseEntity.badRequest().body(errorMessages);
        }

        // Nếu không có lỗi, tiếp tục xử lý logic
        return ResponseEntity.ok("This is Category " + categoryDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("this is update Category ");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("deleteCategory with is " + id);
    }
}
