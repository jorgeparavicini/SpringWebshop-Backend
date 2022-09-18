package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Vendor
import com.jorgeparavicini.springwebshop.database.repositories.VendorRepository
import com.jorgeparavicini.springwebshop.models.VendorDTO

interface VendorService : Service<Vendor, VendorDTO, VendorRepository>