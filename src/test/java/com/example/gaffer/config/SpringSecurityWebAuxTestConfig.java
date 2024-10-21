// package com.example.gaffer.config;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Primary;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// import com.example.gaffer.models.UserEntity;

// @TestConfiguration
// public class SpringSecurityWebAuxTestConfig {

//     private final PasswordEncoder passwordEncoder;

//     public SpringSecurityWebAuxTestConfig(PasswordEncoder passwordEncoder) {
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Bean(name = "testUserDetailsService")
//     @Primary
//     public UserDetailsService testUserDetailsService() {
//         UserEntity adminEntity = new UserEntity();
//         adminEntity.setUsername("a@a.com");
//         adminEntity.setDescription("Hi, I am interested in renting your property. Please let me know what you require and I'll provide it.");
//         adminEntity.setPhoneNumber("0872970140");
//         adminEntity.setName("Ross Murphy");
//         adminEntity.setOccupation("Software Engineer");
//         adminEntity.setProfilePicture("/images/default_user_profile_picture.png");
//         adminEntity.setPassword(passwordEncoder.encode("a"));
//         adminEntity.setEnabled(true);
//         adminEntity.setAccountNonExpired(true);
//         adminEntity.setAccountNonLocked(true);
//         adminEntity.setCredentialsNonExpired(true);
//         adminEntity.setRoles(new ArrayList<>(List.of("ADMIN")));
//         adminEntity.setVerificationCode(null);

//         UserDetails admin = User.withUsername(adminEntity.getUsername())
//             .password(adminEntity.getPassword())
//             .roles(adminEntity.getRoles().toArray(new String[0]))
//             .accountExpired(!adminEntity.isAccountNonExpired())
//             .accountLocked(!adminEntity.isAccountNonLocked())
//             .credentialsExpired(!adminEntity.isCredentialsNonExpired())
//             .disabled(!adminEntity.isEnabled())
//             .build();

//         return new InMemoryUserDetailsManager(Arrays.asList(
//                 admin
//         ));
//     }
// }
