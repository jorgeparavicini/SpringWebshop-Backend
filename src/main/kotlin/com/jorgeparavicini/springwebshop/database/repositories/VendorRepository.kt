package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.Vendor
import org.springframework.data.repository.CrudRepository

interface VendorRepository : CrudRepository<Vendor, Long>