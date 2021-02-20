import React from 'react';

class Main extends React.Component {
    constructor(props) {
        super(props);
    }

    render(){
        return (
            <div>You did it! Now, <a href="/login">log in</a>.</div>
        )
    }
}

export default Main;