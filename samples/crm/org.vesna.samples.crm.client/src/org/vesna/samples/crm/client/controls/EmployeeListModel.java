/*
 * Copyright 2013 Krzysztof Marecki
 *
 * Licensed under te Apache License, Version 2.0 (the "License");
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
package org.vesna.samples.crm.client.controls;

import java.util.List;
import org.vesna.apps.client.controls.EntitiesEditModel;
import org.vesna.apps.client.controls.EntitiesListModel;
import org.vesna.samples.crm.dto.Company;
import org.vesna.samples.crm.dto.Employee;
import org.vesna.samples.crm.entities.EmployeeRepository;

/**
 *
 * @author Krzysztof Marecki
 */
public class EmployeeListModel 
    extends EntitiesListModel<Employee> {
    
    private Company parentCompany;

    public void setParentCompany(Company parentCompany) {
        this.parentCompany = parentCompany;
    }


    @Override
    protected EntitiesEditModel createRowEditModel(EntitiesEditModel.Mode mode) {
        EmployeeEditModel editModel = new EmployeeEditModel(this, entitiesRepository, mode);
        return editModel;
    }

    @Override
    protected String getRepositoryName() {
        return "Employees";
    }

    @Override
    protected List<Employee> getAllEntities() {
        List<Employee> dtos = ((EmployeeRepository)entitiesRepository).getEmployees(parentCompany);
        return dtos;
    }

    @Override
    protected Employee createNewEntity() {
        Employee employee = super.createNewEntity();
        employee.setCompany(parentCompany);
        return employee;
    }
    
}
