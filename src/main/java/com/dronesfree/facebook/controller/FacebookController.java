//package com.dronesfree.facebook.controller;
//
//import java.io.IOException;
//import java.util.UUID;
//
//import javax.servlet.http.HttpSession;
//
//import org.scribe.builder.ServiceBuilder;
//import org.scribe.builder.api.FacebookApi;
//import org.scribe.model.OAuthRequest;
//import org.scribe.model.Response;
//import org.scribe.model.Token;
//import org.scribe.model.Verb;
//import org.scribe.model.Verifier;
//import org.scribe.oauth.OAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionKey;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.connect.FacebookConnectionFactory;
//import org.springframework.social.oauth2.AccessGrant;
//import org.springframework.social.oauth2.GrantType;
//import org.springframework.social.oauth2.OAuth2Operations;
//import org.springframework.social.oauth2.OAuth2Parameters;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
//
//@Controller
//public class FacebookController {
//
//	public static final String STATE = "state";
//	private OAuthService oAuthService;
//	// Jackson ObjectMapper
//	private ObjectMapper objectMapper;
//
//	private final static String FACEBOOK_CLIENID = "1689591751273821";
//	private final static String FACEBOOK_CLIENT_SECRET = "b070fb22886e3ad22efb59073c588804";
//	private final static String APPLICATION_HOST = "http://localhost:8080/Dronesfree";
//	private Facebook facebook;
//	
//	
//	public FacebookController() {
//		this.oAuthService = buildOAuthService(FACEBOOK_CLIENID,
//				FACEBOOK_CLIENT_SECRET);
//		this.objectMapper = new ObjectMapper();
//		this.objectMapper.registerModule(new AfterburnerModule());
//	}
//
//	private OAuthService buildOAuthService(String clientId, String clientSecret) {
//		// The callback must match Site-Url in the Facebook app settings
//		return new ServiceBuilder().apiKey(clientId).apiSecret(clientSecret)
//				.callback(APPLICATION_HOST + "/auth/facebook/callback")
//				.provider(FacebookApi.class).build();
//	
//	}
//
//	@RequestMapping("/auth/facebook")
//	public RedirectView startAuthentication(HttpSession session)
//			throws Exception {
//		String state = UUID.randomUUID().toString();
//		session.setAttribute(STATE, state);
//		String authorizationUrl = oAuthService.getAuthorizationUrl(Token
//				.empty()) + "&" + STATE + "=" + state;
//		return new RedirectView(authorizationUrl);
//		
//	}
//
//	@RequestMapping("/auth/facebook/callback")
//	public String callback(@RequestParam("code") String code,
//			@RequestParam(STATE) String state, HttpSession session)
//			throws IOException {
//		// Check the state parameter
//		String stateFromSession = (String) session.getAttribute(STATE);
//		session.removeAttribute(STATE);
//		if (!state.equals(stateFromSession)) {
//			return "redirect:/login";
//		}
//
//		// Exchange the code for an AccessToken and retrieve the profile
//		Token accessToken = getAccessToken(code);
//		Response response = getResponseForProfile(accessToken);
//		if (!response.isSuccessful()) {
//			return "redirect:/login";
//		}
//
//		// Store the Facebook user id in the session and redirect the user
//		// to the page that needs the profile.
//		String facebookUserId = getFacebookUserId(response);
//		session.setAttribute("facebookUserId", facebookUserId);
//		return "redirect:/user/registration";
//	}
//
//	private Token getAccessToken(String code) {
//		Verifier verifier = new Verifier(code);
//		return oAuthService.getAccessToken(Token.empty(), verifier);
//	}
//
//	private Response getResponseForProfile(Token accessToken) {
//		OAuthRequest oauthRequest = new OAuthRequest(Verb.GET,
//				"https://graph.facebook.com/me");
//		oAuthService.signRequest(accessToken, oauthRequest);
//		return oauthRequest.send();
//	}
//
//	private String getFacebookUserId(Response response) throws IOException {
//		JsonNode jsonNode = objectMapper.readTree(response.getBody());
//		JsonNode idNode = jsonNode.get("id");
//
//		return idNode.asText();
//	}
//
//}
