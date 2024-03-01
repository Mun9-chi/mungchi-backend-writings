package org.mungchi.writings.infra.repository

import org.mungchi.writings.domain.Writings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WritingsRepository : JpaRepository<Writings, Long> {
}
