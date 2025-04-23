/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vab.com.vn.emailnhacno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vab.com.vn.emailnhacno.entity.MailTemplate;

/**
 *
 * @author LAMNTP
 */
@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, String> {

}
