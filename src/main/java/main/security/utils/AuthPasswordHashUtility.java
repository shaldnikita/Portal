package main.security.utils;

import main.models.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthPasswordHashUtility {
	private static final Logger log = LoggerFactory.getLogger(AuthPasswordHashUtility.class);

	public String calculateHash(User user) {
		return calculateHash(user.getSalt(), user.getPassword(), String.valueOf(user.getId()));
	}

	public boolean isValidPassword(User user, String password) {

		if (user == null) {
			return false;
		}

		if (!user.isEnabled())
			return false;

		if (StringUtils.isBlank(user.getHash()) && StringUtils.isBlank(password)) {
			return false;
		}

		String newHash = calculateHash(user.getSalt(), password, String.valueOf(user.getId()));

		if (newHash.equals(user.getHash())) {
			return true;
		}

		return false;

	}

	public String calculateHash(String startSalt, String password, String endSalt) {
		return DigestUtils.sha256Hex(startSalt + ":" + password + ":" + endSalt);
	}
}