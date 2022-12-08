package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.exception.NoCompanyFoundException;
import com.rest.springbootemployee.repository.CompanyMongoRepository;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private CompanyMongoRepository companyMongoRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyMongoRepository companyMongoRepository) {
        this.companyRepository = companyRepository;
        this.companyMongoRepository = companyMongoRepository;
    }

    public List<Company> findAll() {

        return companyMongoRepository.findAll();
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    public Company findById(String companyId) {
        return companyMongoRepository.findById(companyId).orElseThrow(NoCompanyFoundException::new);
    }

    public Company create(Company company) {
        return companyMongoRepository.save(company);
    }

    public void delete(String companyId) {
        companyRepository.delete(companyId);
    }

    public Company update(String companyId, Company toUpdateCompany) {
        Company existingCompany = companyMongoRepository.findById(companyId).orElseThrow(NoCompanyFoundException::new);
        if (toUpdateCompany.getName() != null) {
            existingCompany.setName(toUpdateCompany.getName());
        }
        companyMongoRepository.save(existingCompany);
        return existingCompany;
    }

    public List<Employee> getEmployees(String companyId) {
        Company company = companyRepository.findById(companyId);
        return company.getEmployees();
    }

}
