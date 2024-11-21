package com.jpabook2.jpashop2.api;

import com.jpabook2.jpashop2.domain.Member;
import com.jpabook2.jpashop2.service.MemberService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 조회
     */
    @GetMapping("/api/v1/members")
    private List<Member> Members() {
        return memberService.findMemberAll();
    }

    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> membersV2() {
        List<Member> findMembers = memberService.findMemberAll();
        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result<>(collect.size(),collect);

    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int size;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }





    /**
     * 등록 V1: 요청 값으로 Member 엔티티를 직접 받는다.
     * 문제점
     * - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
     * - 엔티티에 API 검증을 위한 로직이 들어간다. (@NotEmpty 등등)
     * - 실무에서는 회원 엔티티를 위한 API가 다양하게 만들어지는데, 한 엔티티에 각각의 API를 위한
     모든 요청 요구사항을 담기는 어렵다.
     * - 엔티티가 변경되면 API 스펙이 변한다.
     * 결론
     * - API 요청 스펙에 맞추어 별도의 DTO를 파라미터로 받는다.
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse CreateMemberResponse(@RequestBody @Valid Member member) throws Exception { // 엔티티자체를 넘기면 안된다.
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 등록 V2: 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다.
     */
    @PostMapping("/api/v2/members")
    private CreateMemberResponse CreateMemberResponseV2(@RequestBody @Valid CreateMemberReqeustDto createMemberReqeustDto) throws Exception {
        Member member = new Member();
        member.setName(createMemberReqeustDto.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 수정
     */
    @PutMapping("/api/v2/member/{id}")
    private UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateMemberRequest request){

        memberService.update(id, request.getName());  // 커맨드와 쿼리 분리
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class CreateMemberReqeustDto {
        private String name;

    }

    @Data
    @NoArgsConstructor
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
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


}
