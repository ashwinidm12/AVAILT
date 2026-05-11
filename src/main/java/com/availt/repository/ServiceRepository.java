package com.availt.repository;

import com.availt.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByCategoryIgnoreCase(String category);

    @Query("select distinct s.category from ServiceEntity s order by s.category")
    List<String> findDistinctCategories();

    @Query("select s from ServiceEntity s order by case when coalesce(s.communitySubmitted, false) = true then 1 else 0 end asc, s.id asc")
    List<ServiceEntity> findAllOrderedSeedFirst();

    @Query("select s from ServiceEntity s where lower(s.category) = lower(:cat) order by case when coalesce(s.communitySubmitted, false) = true then 1 else 0 end asc, s.id asc")
    List<ServiceEntity> findByCategoryOrderedSeedFirst(@Param("cat") String category);
}