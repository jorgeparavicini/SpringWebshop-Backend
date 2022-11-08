package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Vendor
import com.jorgeparavicini.springwebshop.database.repositories.VendorRepository
import com.jorgeparavicini.springwebshop.dto.VendorDTO

interface VendorService : Service<Vendor, VendorDTO, VendorRepository>