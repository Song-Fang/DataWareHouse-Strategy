export class Users {
  user: User;
  token: string;
}

export class User {
  id: number;
  username: string;
  password: string;
  phonenumber: string;
  email: string;
  state: string;
  city: string;
  isAdmin: boolean;
  isAnonymous: boolean;
}
