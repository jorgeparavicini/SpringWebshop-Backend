package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.UserAddress
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserAddressRepository : BaseRepository<UserAddress> {
    fun getAllByUserId(userId: String): List<UserAddress>
    fun findByUserIdAndId(userId: String, id: Long): Optional<UserAddress>

    @Query("update #{#entityName} e set e.deleted = true where e.id=?1 AND e.userId=?2")
    @Modifying
    fun softDelete(id: Long, userId: String)
}