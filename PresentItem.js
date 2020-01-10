import React from 'react';
import { StyleSheet, View, 
  StatusBar, ListView, TextInput, TouchableOpacity} from 'react-native';

import firebase from 'firebase';

import { Container, Header, 
  Content, ActionSheet, 
  Text, Input, Icon, List, 
  ListItem, Item, Grid } from "native-base";

//import GetNewEntry from'./getNewEntry';
import Modal from 'react-native-modalbox';
import Button from 'react-native-button';

import DateTimePicker from 'react-native-modal-datetime-picker';

import Expo from 'expo';
import  { createStackNavigator }  from 'react-navigation';


class PresentItem extends React.Component {

render()
  {
     return( 
        <View>
          <Text>Task Profile</Text>
            <Text style = {{alignItems: 'center',
                justifyContent : 'center',
                textAlign: 'center',
                margin: 5,
                padding : 10,
                borderColor: '#ff1a1a',
                borderWidth: 2,
                borderRadius: 10,
                overflow: 'hidden'}}>
                { this.props.navigation.state.params.Name} </Text>
            <Text style = {{alignItems: 'center',
                justifyContent : 'center',
                textAlign: 'center',
                margin: 5,
                padding : 10,
                borderColor: '#ff1a1a',
                borderWidth: 2,
                borderRadius: 10,
                overflow: 'hidden'}}>
                { this.props.navigation.state.params.Discription} </Text>
            <Text style = {{alignItems: 'center',
                justifyContent : 'center',
                textAlign: 'center',
                margin: 5,
                padding : 10,
                borderColor: '#ff1a1a',
                borderWidth: 2,
                borderRadius: 10,
                overflow: 'hidden'}}>
                { this.props.navigation.state.params.Date} </Text>

        </View>
     );
  }
} export default PresentItem;