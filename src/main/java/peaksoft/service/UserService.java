package peaksoft.service;

import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.SignResponse;
import peaksoft.dto.response.SimpleResponse;

/**
 * @author Mukhammed Asantegin
 */
public interface UserService {
    SimpleResponse signUp(SignUpRequest signUpRequest);

    SignResponse signIn(SignInRequest signInRequest);
}
