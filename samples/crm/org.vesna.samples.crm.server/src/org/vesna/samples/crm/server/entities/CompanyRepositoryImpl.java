/*
 * Copyright 2013 Krzysztof Marecki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vesna.samples.crm.server.entities;

import org.vesna.core.server.entities.RepositoryImpl;
import org.vesna.samples.crm.dto.Company;
import org.vesna.samples.crm.entities.CompanyRepository;

/**
 *
 * @author Krzysztof Marecki
 */
public class CompanyRepositoryImpl 
    extends RepositoryImpl<Company> implements CompanyRepository {

    @Override
    public Company insert(Company entity) {
        return super.insert(entity); 
    }

    @Override
    public Company update(Company entity) {
        return super.update(entity); 
    }

    @Override
    public void delete(Company entity) {
        super.delete(entity); 
    }
    
    public Company getSingle(int id) {
        return super.getSingle(id); 
    }
   
}
