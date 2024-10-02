package com.dvl.Ecommer.repositories;


import com.dvl.Ecommer.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
