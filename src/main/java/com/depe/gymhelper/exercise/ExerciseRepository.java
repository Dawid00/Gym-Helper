package com.depe.gymhelper.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
