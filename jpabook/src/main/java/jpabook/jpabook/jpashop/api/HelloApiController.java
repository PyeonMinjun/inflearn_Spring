package jpabook.jpabook.jpashop.api;


import jakarta.validation.Valid;
import jpabook.jpabook.jpashop.domain.Address;
import jpabook.jpabook.jpashop.domain.Member;
import jpabook.jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.xml.namespace.QName;

//@Controller
//@ResponseBody
@RestController
@Slf4j
@RequiredArgsConstructor
public class HelloApiController {
    private final MemberService memberService;

    /**
     * 등록 v1 : 요청값으로 Member 엔티티를 직접 받는다.
     * 문제점
     * - 엔티티 프레젠테이션 계층을 위한 로직이 추가된다.
     * -엔티티에 API 검증을 위한 로직이 들어간다 (@NotEmpty 등등)
     * -실무에서는 회원 엔티티를 위한 API가 다양하게 만들어지는데, 한 엔티티에 각각의 API를 위한 모든 요청 요구사항을 담기는 어렵다.
     * -엔티티가 변경되면 API 스펙이 변한다.
     * 결론
     * - API 요청 스펙에 맞추어 별도의 DTO를 파라미터로 받는다.
     */

    @PostMapping("/api/v1/members")
    private CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { //엔티티를 파라미터로 받지말자.
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 등록 V2 : 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다.
     */


    @PostMapping("/api/v2/members")
    private CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Address address = new Address(request.city, request.street, request.zipcode);
        member.setAddress(address);

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    private UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());

    }


    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }


    @Data
    static class CreateMemberRequest {
        private String name;
        private String city;
        private String street;
        private String zipcode;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}