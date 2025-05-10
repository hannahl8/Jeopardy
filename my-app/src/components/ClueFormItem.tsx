import React from 'react';
import TextInput from './form/TextInput';
import TextArea from "./form/TextArea.tsx";

interface ClueFormItemProps {
    index: number;
    categoryIndex: number;
}

const ClueFormItem: React.FC<ClueFormItemProps> = ({index, categoryIndex}) => {
    return (
        <div  className="clue-div container">
            <h3>Clue {index + 1}</h3>
            <TextArea label="Question:" name={`categoryRequests[${categoryIndex}].clueRequests[${index}].question`}/>
            <TextArea label="Answer:" name={`categoryRequests[${categoryIndex}].clueRequests[${index}].answer`}/>
            <TextInput label="Value:" name={`categoryRequests[${categoryIndex}].clueRequests[${index}].value`}
                       type="number"/>
        </div>
    );
};

export default ClueFormItem;