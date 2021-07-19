import React from "react";

let interval;
export default class HomePage extends React.Component {
    state = {
        time : new Date().toLocaleTimeString(),
        clickTime: 0,
    }

    async clickGame() {
        await this.setState({clickTime:this.state.clickTime + 1});
        if (this.state.clickTime % 15 === 0) alert("FizzBuzz");
        else if (this.state.clickTime % 5 === 0) alert("Buzz");
        else if (this.state.clickTime % 3 === 0) alert("Fizz");
    }

    componentDidMount() {
        interval = setInterval(() => this.setState({ time: new Date().toLocaleTimeString}), 1000);
        /*if (time > ) {
            clearInterval(interval);
        }*/
    }

    componentWillUnmount() {
        clearInterval(interval);
    }

    /*useEffect(() => { // for functional component we use useEffect
        console.log("did mount"); // did mount
        interval = setInterval(() => this.setState({ time: new Date().toLocaleTimeString}), 1000);
        return () => {
            //will unmout
            clearInterval(interval);
        }
    }, []);*/

    render () {
        return (
            <div> 
                Welcome to {this.props.bootcamp}, {this.props.name}. It is {this.state.time}
                <button onClick={this.clickGame}> Clicky Game</button>
            </div>
        );
    }
}

