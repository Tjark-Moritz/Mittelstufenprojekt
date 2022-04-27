export class BearerToken {
  constructor(
    public access_token?: string,
    public expires_in?: number,
    public refresh_expires_in?: number,
    public refresh_token?: string,
    public token_type?: string,
    public not_before_policy?: number,
    public session_state?: string,
    public scope?: string) { };
}
