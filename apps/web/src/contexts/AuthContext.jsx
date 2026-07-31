import { createContext, useEffect, useMemo, useState } from "react";
import { authService } from "../services/authService";

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem("foody_token"));
  const [user, setUser] = useState(() => {
    const raw = localStorage.getItem("foody_user");
    return raw ? JSON.parse(raw) : null;
  });

  useEffect(() => {
    if (token) {
      localStorage.setItem("foody_token", token);
    } else {
      localStorage.removeItem("foody_token");
    }
  }, [token]);

  useEffect(() => {
    if (user) {
      localStorage.setItem("foody_user", JSON.stringify(user));
    } else {
      localStorage.removeItem("foody_user");
    }
  }, [user]);

  const login = async (credentials) => {
    const response = await authService.login(credentials);
    setToken(response.token);
    setUser({ nome: response.nome, email: response.email });
    return response;
  };

  const register = async (payload) => {
    const response = await authService.register(payload);
    setToken(response.token);
    setUser({ nome: response.nome, email: response.email });
    return response;
  };

  const logout = () => {
    setToken(null);
    setUser(null);
  };

  const value = useMemo(
    () => ({
      token,
      user,
      isAuthenticated: Boolean(token),
      login,
      register,
      logout
    }),
    [token, user]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
