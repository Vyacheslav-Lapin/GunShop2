class BaseServer {

    // noinspection SpellCheckingInspection
    constructor(private readonly method: string = `GET`,
                private readonly baseUrl: string = `/webapi/`) {
    }

    protected getObject<T>(urlPart: string): Promise<T> {
        return fetch(this.baseUrl + urlPart, { credentials: "same-origin" })
            .then(response => response.json())
            .catch(status => Error(`JSON didn't load successfully; error code: ${status}`));
    }
}
