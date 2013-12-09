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
package org.vesna.samples.crm.client.entities;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.vesna.core.client.entities.RepositoryImpl;
import org.vesna.samples.crm.dto.Company;
import org.vesna.samples.crm.dto.Employee;
import org.vesna.samples.crm.entities.EmployeeRepository;

/**
 *
 * @author Krzysztof Marecki
 */
public class EmployeeRepositoryImpl
    extends RepositoryImpl<Employee> implements EmployeeRepository{

    @Override
    protected TypeToken getTEntityTypeToken() {
        return new TypeToken<Employee>(){};
    }

    @Override
    protected TypeToken getListTEntityTypeToken() {
        return new TypeToken<List<Employee>>(){};
    }

    @Override
    public List<Employee> getEmployees(Company company) {
        return execGetList("getEmployees", new Object[] { company });
    }
    
}
